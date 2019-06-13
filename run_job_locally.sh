# Enviroment variables
INPUT_PATH=./data/input
OUTPUT_PATH=./data/results

# Create input and output data directories
mkdir -p $OUTPUT_PATH

# Package the Scala application
sbt package

# Run job locally
spark-submit \
    --class "DataprocJob" \
    --master local \
    target/scala-2.11/dataproc-job_2.11-1.0.jar \
    "$INPUT_PATH/*.json" \
    $OUTPUT_PATH
