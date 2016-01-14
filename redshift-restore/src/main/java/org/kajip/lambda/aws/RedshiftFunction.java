package org.kajip.lambda.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.redshift.model.Cluster;
import org.kajip.aws.Redshift;

public class RedshiftFunction {

    Redshift redshift = new Redshift(Regions.AP_NORTHEAST_1);

    public Cluster handler(Context context) {
        return redshift.restoreCluster();
    }
}
