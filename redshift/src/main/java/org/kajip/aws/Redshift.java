package org.kajip.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.redshift.AmazonRedshiftClient;
import com.amazonaws.services.redshift.model.*;
import org.kajip.config.ClasspathProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class Redshift {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Properties properties = new ClasspathProperties("lambda/redshift.properties");


    private final String clusterIdentifier;

    private final SystemTags systemTags = new SystemTags();


    private final AmazonRedshiftClient redshift;

    private final AmazonEC2Client ec2;


    final ClusterParameterGroup clusterParameterGroup;

    final ClusterSubnetGroup clusterSubnetGroup;

    final List<SecurityGroup> securityGroups;


    public Redshift() {

        this.clusterIdentifier = properties.getProperty("id");

        redshift = new AmazonRedshiftClient();
        ec2 = new AmazonEC2Client();

        clusterParameterGroup = _getClusterParameterGroupName();
        clusterSubnetGroup = _getClusterSubnetGroup();
        securityGroups = _getSecurityGroups(clusterSubnetGroup.getVpcId());
    }

    public Redshift(Regions regions) {

        this.clusterIdentifier = properties.getProperty("id");

        redshift = new AmazonRedshiftClient().withRegion(regions);
        ec2 = new AmazonEC2Client().withRegion(regions);

        clusterParameterGroup = _getClusterParameterGroupName();
        clusterSubnetGroup = _getClusterSubnetGroup();
        securityGroups = _getSecurityGroups(clusterSubnetGroup.getVpcId());
    }

    private ClusterParameterGroup _getClusterParameterGroupName() {
        return redshift.describeClusterParameterGroups(new DescribeClusterParameterGroupsRequest()
                .withTagKeys(SystemTags.ENVIRONMENT_KEY)
                .withTagValues(systemTags.getEnvironment())
        ).getParameterGroups().stream().findFirst().get();
    }

    private ClusterSubnetGroup _getClusterSubnetGroup() {
        return redshift.describeClusterSubnetGroups(new DescribeClusterSubnetGroupsRequest()
                .withTagKeys(SystemTags.ENVIRONMENT_KEY)
                .withTagValues(systemTags.getEnvironment())
        ).getClusterSubnetGroups().stream().findFirst().get();
    }

    private List<SecurityGroup> _getSecurityGroups(String vpcId) {
        return ec2.describeSecurityGroups(new DescribeSecurityGroupsRequest()
                .withFilters(
                        new Filter("tag-key", Arrays.asList(SystemTags.SYSTEM_KEY)),
                        new Filter("tag-value", Arrays.asList(systemTags.getSystem())),
                        new Filter("vpc-id", Arrays.asList(vpcId))
                )
        ).getSecurityGroups();
    }

    public Optional<Snapshot> getLastSnapshots() {
        List<Snapshot> snapshots = redshift
                .describeClusterSnapshots(new DescribeClusterSnapshotsRequest()
                        .withClusterIdentifier(clusterIdentifier)
                        .withSnapshotType("manual")
                ).getSnapshots();

        return snapshots.stream()
                .sorted((a,b) -> b.getSnapshotCreateTime().compareTo(a.getSnapshotCreateTime()))
                .findFirst();
    }

    DateTimeFormatter snapshotIdentifierFormatter =
            DateTimeFormatter.ofPattern("'develop-'yyyyMMdd-HHmmss");

    public Cluster shutdownCluster() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        String snapshotIdentifier = currentDateTime.format(snapshotIdentifierFormatter);

        logger.debug("shutdown cluster and create Snapshot({})", snapshotIdentifier);

        return redshift.deleteCluster(new DeleteClusterRequest()
                .withClusterIdentifier(clusterIdentifier)
                .withFinalClusterSnapshotIdentifier(snapshotIdentifier)
        );
    }

    public Cluster restoreCluster() {

        Snapshot snapshot = getLastSnapshots().get();

        logger.debug("restore from Snapshot({})", snapshot.getSnapshotIdentifier());

        return redshift.restoreFromClusterSnapshot(new RestoreFromClusterSnapshotRequest()
                .withSnapshotIdentifier(snapshot.getSnapshotIdentifier())
                .withClusterIdentifier(snapshot.getClusterIdentifier())
                .withClusterParameterGroupName(clusterParameterGroup.getParameterGroupName())
                .withClusterSubnetGroupName(clusterSubnetGroup.getClusterSubnetGroupName())
                .withVpcSecurityGroupIds(securityGroups.stream()
                        .map(SecurityGroup::getGroupId)
                        .collect(Collectors.toList()))
        );
    }
}
