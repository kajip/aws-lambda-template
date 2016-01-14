package org.kajip.aws;

import static com.amazonaws.regions.Regions.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


public class RedshiftFunctionTest {

    @Test
    public void shutdownClusterのテスト() throws Exception {

        Redshift redshift = new Redshift(AP_NORTHEAST_1);

        assertThat(
                redshift.clusterParameterGroup.getParameterGroupName(), is("sampleparametergroup"));

        assertThat(
                redshift.clusterSubnetGroup.getClusterSubnetGroupName(), is("subnet-group-test-network-development"));
        assertThat(
                redshift.clusterSubnetGroup.getVpcId(), is("vpc-d5bd34b0"));



//        redshift.restoreCluster();

//        redshift.shutdownCluster();
    }
}
