# Spark on Google Cloud

This is a short demo of how to run a simple spark jobs on Dataproc on the Google Cloud Plataform.

The project runs some simple transformations on cryptocurrency data. There are some sample files on the folder "data". To run the project you do it locally or on the cloud.

The script `run_job_locally.sh` will run the job locally. To perform this task you already have a standalone spark cluster on your machine. This is useful for testing your code locally before submitting it to the cloud.

To run in the cloud will have to use the script `run_job_in_the_cloud.sh` . We have to do some actions before executing the script:
*  Create a Google Cloud account
*  Install GCP SDK
* Install Scala Build Tool (SBT)
* Create a Dataproc Cluster on GCP
* Create a data bucket (GCS) on GCP

After the previous steps just update the script with your proper configuration.
