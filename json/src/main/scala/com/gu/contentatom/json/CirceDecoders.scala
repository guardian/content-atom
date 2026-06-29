package com.gu.contentatom.json

import com.gu.contentatom.thrift._
import com.gu.contentatom.thrift.atom.audio._
import com.gu.contentatom.thrift.atom.chart._
import com.gu.contentatom.thrift.atom.commonsdivision._
import com.gu.contentatom.thrift.atom.cta._
import com.gu.contentatom.thrift.atom.emailsignup._
import com.gu.contentatom.thrift.atom.explainer._
import com.gu.contentatom.thrift.atom.interactive._
import com.gu.contentatom.thrift.atom.guide._
import com.gu.contentatom.thrift.atom.media.{Asset => MediaAsset, _}
import com.gu.contentatom.thrift.atom.profile._
import com.gu.contentatom.thrift.atom.qanda._
import com.gu.contentatom.thrift.atom.quiz.{Asset => QuizAsset, _}
import com.gu.contentatom.thrift.atom.review._
import com.gu.contentatom.thrift.atom.timeline._
import com.gu.contententity.json.CirceDecoders._
import com.gu.fezziwig.CirceScroogeMacros._
import com.gu.fezziwig.CirceScroogeWhiteboxMacros._
import io.circe.Decoder
import io.circe.generic.semiauto._

object CirceDecoders {
  // atoms/audio.thrift
  implicit lazy val offPlatformDecoder: Decoder[OffPlatform] = deriveDecoder
  implicit lazy val audioAtomDecoder: Decoder[AudioAtom] = deriveDecoder

  // atoms/chart.thrift
  implicit lazy val chartTypeDecoder: Decoder[ChartType] = deriveDecoder
  implicit lazy val rangeDecoder: Decoder[Range] = deriveDecoder
  implicit lazy val displaySettingsDecoder: Decoder[DisplaySettings] = deriveDecoder
  implicit lazy val furnitureDecoder: Decoder[Furniture] = deriveDecoder
  implicit lazy val rowTypeDecoder: Decoder[RowType] = deriveDecoder
  implicit lazy val tabularDataDecoder: Decoder[TabularData] = deriveDecoder
  implicit lazy val seriesColourDecoder: Decoder[SeriesColour] = deriveDecoder
  implicit lazy val axisDecoder: Decoder[Axis] = deriveDecoder
  implicit lazy val chartAtomDecoder: Decoder[ChartAtom] = deriveDecoder

  // atoms/commonsdivision.thrift
  implicit lazy val mPDecoder: Decoder[Mp] = deriveDecoder
  implicit lazy val votesDecoder: Decoder[Votes] = deriveDecoder
  implicit lazy val commonsDivisionDecoder: Decoder[CommonsDivision] = deriveDecoder

  // atoms/cta.thrift
  implicit lazy val cTAAtomDecoder: Decoder[CTAAtom] = deriveDecoder

  // atoms/emailsignup.thrift
  implicit lazy val emailSignUpAtomDecoder: Decoder[EmailSignUpAtom] = deriveDecoder

  // atoms/explainer.thrift
  implicit lazy val displayTypeDecoder: Decoder[DisplayType] = deriveDecoder
  implicit lazy val explainerAtomDecoder: Decoder[ExplainerAtom] = deriveDecoder

  // atoms/guide.thrift
  implicit lazy val guideItemDecoder: Decoder[GuideItem] = deriveDecoder
  implicit lazy val guideAtomDecoder: Decoder[GuideAtom] = deriveDecoder

  // atoms/interactive.thrift
  implicit lazy val interactiveAtomDecoder: Decoder[InteractiveAtom] = deriveDecoder

  // atoms/media.thrift
  implicit lazy val platformDecoder: Decoder[Platform] = deriveDecoder
  implicit lazy val assetTypeDecoder: Decoder[AssetType] = deriveDecoder
  implicit lazy val categoryDecoder: Decoder[Category] = deriveDecoder
  implicit lazy val privacyStatusDecoder: Decoder[PrivacyStatus] = deriveDecoder
  implicit lazy val videoPlayerFormatDecoder: Decoder[VideoPlayerFormat] = deriveDecoder
  implicit lazy val mediaAssetDecoder: Decoder[MediaAsset] = deriveDecoder
  implicit lazy val plutoDataDecoder: Decoder[PlutoData] = deriveDecoder
  implicit lazy val iconikDataDecoder: Decoder[IconikData] = deriveDecoder
  implicit lazy val youtubeDataDecoder: Decoder[YoutubeData] = deriveDecoder
  implicit lazy val selfHostDataDecoder: Decoder[SelfHostData] = deriveDecoder
  implicit lazy val metadataDecoder: Decoder[Metadata] = deriveDecoder
  implicit lazy val mediaAtomDecoder: Decoder[MediaAtom] = deriveDecoder

  // atoms/profile.thrift
  implicit lazy val profileItemDecoder: Decoder[ProfileItem] = deriveDecoder
  implicit lazy val profileAtomDecoder: Decoder[ProfileAtom] = deriveDecoder

  // atoms/qanda.thrift
  implicit lazy val qAndAItemDecoder: Decoder[QAndAItem] = deriveDecoder
  implicit lazy val qAndAAtomDecoder: Decoder[QAndAAtom] = deriveDecoder

  // atoms/quiz.thrift
  implicit lazy val resultGroupDecoder: Decoder[ResultGroup] = deriveDecoder
  implicit lazy val quizAssetDecoder: Decoder[QuizAsset] = deriveDecoder
  implicit lazy val answerDecoder: Decoder[Answer] = deriveDecoder
  implicit lazy val resultBucketDecoder: Decoder[ResultBucket] = deriveDecoder
  implicit lazy val resultBucketsDecoder: Decoder[ResultBuckets] = deriveDecoder
  implicit lazy val questionDecoder: Decoder[Question] = deriveDecoder
  implicit lazy val resultGroupsDecoder: Decoder[ResultGroups] = deriveDecoder
  implicit lazy val quizContentDecoder: Decoder[QuizContent] = deriveDecoder
  implicit lazy val quizAtomDecoder: Decoder[QuizAtom] = deriveDecoder

  // atoms/review.thrift
  implicit lazy val reviewTypeDecoder: Decoder[ReviewType] = deriveDecoder
  implicit lazy val ratingDecoder: Decoder[Rating] = deriveDecoder
  implicit lazy val reviewAtomDecoder: Decoder[ReviewAtom] = deriveDecoder

  // atoms/shared.thrift
  implicit lazy val userDecoder: Decoder[User] = deriveDecoder
  implicit lazy val changeRecordDecoder: Decoder[ChangeRecord] = deriveDecoder
  implicit lazy val sectionDecoder: Decoder[Section] = deriveDecoder
  implicit lazy val tagDecoder: Decoder[Tag] = deriveDecoder
  implicit lazy val newspaperDecoder: Decoder[Newspaper] = deriveDecoder
  implicit lazy val tagUsageDecoder: Decoder[TagUsage] = deriveDecoder
  implicit lazy val referenceDecoder: Decoder[Reference] = deriveDecoder
  implicit lazy val taxonomyDecoder: Decoder[Taxonomy] = deriveDecoder
  implicit lazy val imageAssetDimensionsDecoder: Decoder[ImageAssetDimensions] = deriveDecoder
  implicit lazy val imageAssetDecoder: Decoder[ImageAsset] = deriveDecoder
  implicit lazy val imageDecoder: Decoder[Image] = deriveDecoder
  implicit lazy val emailProviderDecoder: Decoder[EmailProvider] = deriveDecoder
  implicit lazy val notificationProvidersDecoder: Decoder[NotificationProviders] = deriveDecoder

  // atoms/timeline.thrift
  implicit lazy val timelineItemDecoder: Decoder[TimelineItem] = deriveDecoder
  implicit lazy val timelineAtomDecoder: Decoder[TimelineAtom] = deriveDecoder

  // contentatom.thrift
  implicit lazy val atomTypeDecoder: Decoder[AtomType] = deriveDecoder
  implicit lazy val atomDataDecoder: Decoder[AtomData] = deriveDecoder
  implicit lazy val contentChangeDetailsDecoder: Decoder[ContentChangeDetails] = deriveDecoder
  implicit lazy val flagsDecoder: Decoder[Flags] = deriveDecoder
  implicit lazy val atomDecoder: Decoder[Atom] = deriveDecoder
  implicit lazy val eventTypeDecoder: Decoder[EventType] = deriveDecoder
  implicit lazy val contentAtomEventDecoder: Decoder[ContentAtomEvent] = deriveDecoder
}
