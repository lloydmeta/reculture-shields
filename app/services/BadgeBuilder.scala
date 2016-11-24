package services

import com.netaporter.uri.Uri
import com.netaporter.uri.dsl._
import org.culture.{OrgId, OrgInfo}
import play.api.libs.ws.{StreamedResponse, WSClient}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Super simple badge builder for Re:Culture based status
  */
class BadgeBuilder(wsClient: WSClient)(implicit val ec: ExecutionContext) {

  /**
    * Retrieves Org info from re:culture and spits out badge from Shields.io
    *
    * Everything is non-blocking. The response from shields.io is streamed.
    */
  def get(orgId: OrgId): Future[StreamedResponse] = {
    getInfo(orgId).flatMap(getBadge)
  }

  // Super basic.

  private def getInfo(orgId: OrgId): Future[OrgInfo] = {
    wsClient
      .url(s"https://www.reculture.us/api/public/organizations/${orgId.id}")
      .get()
      .map(_.json.as[OrgInfo])
  }

  private def getBadge(info: OrgInfo): Future[StreamedResponse] = {
    val badgeName = s"Re:Culture-${info.name} ${info.status}-blue"
    val uri: Uri = s"https://img.shields.io/badge/$badgeName.svg"
    wsClient.url(uri).withQueryString("style" -> "plastic").stream()
  }

}
