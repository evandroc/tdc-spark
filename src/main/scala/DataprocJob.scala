import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions.{col, dayofweek, from_unixtime, input_file_name, regexp_extract, udf, lit}
import org.apache.spark.ml.feature.OneHotEncoderEstimator
import org.apache.spark.ml.linalg.{SparseVector, DenseVector}


object DataprocJob {
  def main(args: Array[String]): Unit = {
    val inputPath = args(0)
    val outputPath = s"${args(1)}/output_${System.currentTimeMillis()}"

    val spark = SparkSession.builder()
                            .appName("Big Data Workshop Hands-On")
                            .config("spark.sql.orc.impl", "native")
                            .getOrCreate()

    val originalDf = spark.read.json(inputPath)

    val cryptoPriceWithoutDateDf = originalDf.select(
      col("Symbol").alias("symbol"),
      col("quotes.USD.price").alias("price"),
      input_file_name().alias("file_path")
    )

    val bitcoinPriceWithoutDateDf = cryptoPriceWithoutDateDf.where("(price IS NOT NULL) AND (symbol = \"BTC\")")

    val bitcoinPriceDf = bitcoinPriceWithoutDateDf.withColumn("datetime", regexp_extract(col("file_path"), ".*/(.*).json", 1))
                                                  .withColumn("datetime", from_unixtime(col("datetime")))
                                                  .withColumn("dayofweek", dayofweek(col("datetime")))

    bitcoinPriceDf.write.format("orc").save(outputPath)
  }
}
