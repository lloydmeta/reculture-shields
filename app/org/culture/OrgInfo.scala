package org.culture

import play.api.libs.json.{JsNumber, Json, Reads, Writes}

object OrgId {
  // Json serdes for our wrapper class
  implicit val reader = Reads.of[Long].map(OrgId.apply)
  implicit val writer = Writes { (orgId: OrgId) =>
    JsNumber(orgId.id)
  }
}
case class OrgId(id: Long) extends AnyVal

object OrgInfo {
  implicit val jsonFormats = Json.format[OrgInfo]
}
case class OrgInfo(id: OrgId, name: String, status: Int)
