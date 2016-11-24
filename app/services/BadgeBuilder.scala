package services

import java.net.URI

import org.culture.{OrgId, OrgInfo}
import play.api.libs.ws.{StreamedResponse, WSClient}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Lloyd on 11/24/16.
  *
  * Copyright 2016
  */
class BadgeBuilder(wsClient: WSClient)(implicit val ec: ExecutionContext) {

  /**
    * Retrieves Org info from re:culture and spits out badge from Shields.io
    *
    * Everything is non-blocking. The response from shields.io is streamed
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
    val badgeName = s"Diversity-${info.name}:${info.status}-blue"
    val uri = new URI(s"https://img.shields.io/badge/$badgeName.svg")
    wsClient
      .url(uri.toASCIIString)
      .withQueryString("stype" -> "plastic")
      .stream()
  }

}
