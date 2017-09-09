package io.github.easel

import org.scalatest.{MustMatchers, WordSpec}
import spray.json._
import DefaultJsonProtocol._

class SprayJsonExamples extends WordSpec with MustMatchers {
  val testStr: String = scala.io.Source.fromResource("facets.json").mkString
  lazy val testJson: JsValue = testStr.parseJson
  "spray json" should {
    "parse the json file" in {
      testJson mustBe a[JsValue]
    }
    "extract keys from solr facets" in {
      val array = testJson.asJsObject
        .fields("facets")
        .asJsObject
        .fields("duplicate-an-id")
        .asJsObject
        .fields("buckets")
        .asInstanceOf[JsArray]
      val keys = array.elements
        .map(_.asJsObject.fields("val").asInstanceOf[JsString].value)
        .toArray
      keys mustEqual
        Array("2.0.0.04bf0588-bd1a-4633-9112-899a47eaf8.08",
              "2.0.01.7001ab9-de12-45.02-8073-172a44b51621",
              "2.0.0.97f3468e-412b-4543-bf13-496c18514.035")
    }
  }

}
