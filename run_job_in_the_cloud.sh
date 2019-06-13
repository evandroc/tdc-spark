# Enviroment variables
CLUSTER_NAME=cluster-tdc
JOB_BUCKET=gs://tdc_data/bin/
INPUT_DATA_BUCKET=gs://tdc_data/input
OUTPUT_DATA_BUCKET=gs://tdc_data/output 

# Copy local data to your bucket
gsutil -m cp data/input/*.json $INPUT_DATA_BUCKET

# Package Scala application
sbt package
PACKAGE_NAME=dataproc-job_2.11-1.0.jar
# Copy JAR to Cloud Storage
gsutil -m cp target/scala-2.11/$PACKAGE_NAME $JOB_BUCKET

# Run Dataproc job
gcloud dataproc jobs submit spark --cluster $CLUSTER_NAME \
                                  --region us-east1 \
                                  --jar $JOB_BUCKET/$PACKAGE_NAME \
                                  -- \
                                  "$INPUT_DATA_BUCKET/*.json" \
                                  "$OUTPUT_DATA_BUCKET"
