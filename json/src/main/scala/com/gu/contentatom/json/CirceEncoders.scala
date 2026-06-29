package com.gu.contentatom.json

import com.gu.contentatom.thrift._
import com.gu.contentatom.thrift.atom.audio._
import com.gu.contentatom.thrift.atom.chart._
import com.gu.contentatom.thrift.atom.commonsdivision._
import com.gu.contentatom.thrift.atom.cta._
import com.gu.contentatom.thrift.atom.emailsignup._
import com.gu.contentatom.thrift.atom.explainer._
import com.gu.contentatom.thrift.atom.guide._
import com.gu.contentatom.thrift.atom.interactive._
import com.gu.contentatom.thrift.atom.media.{Asset => MediaAsset, _}
import com.gu.contentatom.thrift.atom.profile._
import com.gu.contentatom.thrift.atom.qanda._
import com.gu.contentatom.thrift.atom.quiz.{Asset => QuizAsset, _}
import com.gu.contentatom.thrift.atom.review._
import com.gu.contentatom.thrift.atom.timeline._
import com.gu.contententity.json.CirceEncoders._
import com.gu.fezziwig.CirceScroogeMacros._
import com.gu.fezziwig.CirceScroogeWhiteboxMacros._
import io.circe.Encoder
import io.circe.generic.semiauto._

object CirceEncoders {
  // atoms/audio.thrift
  implicit lazy val offPlatformEncoder: Encoder[OffPlatform] = deriveEncoder
  implicit lazy val audioAtomEncoder: Encoder[AudioAtom] = deriveEncoder

  // atoms/chart.thrift
  implicit lazy val chartTypeEncoder: Encoder[ChartType] = deriveEncoder
  implicit lazy val rangeEncoder: Encoder[Range] = deriveEncoder
  implicit lazy val displaySettingsEncoder: Encoder[DisplaySettings] = deriveEncoder
  implicit lazy val furnitureEncoder: Encoder[Furniture] = deriveEncoder
  implicit lazy val rowTypeEncoder: Encoder[RowType] = deriveEncoder
  implicit lazy val tabularDataEncoder: Encoder[TabularData] = deriveEncoder
  implicit lazy val seriesColourEncoder: Encoder[SeriesColour] = deriveEncoder
  implicit lazy val axisEncoder: Encoder[Axis] = deriveEncoder
  implicit lazy val chartAtomEncoder: Encoder[ChartAtom] = deriveEncoder

  // atoms/commonsdivision.thrift
  implicit lazy val mPEncoder: Encoder[Mp] = deriveEncoder
  implicit lazy val votesEncoder: Encoder[Votes] = deriveEncoder
  implicit lazy val commonsDivisionEncoder: Encoder[CommonsDivision] = deriveEncoder

  // atoms/cta.thrift
  implicit lazy val cTAAtomEncoder: Encoder[CTAAtom] = deriveEncoder

  // atoms/emailsignup.thrift
  implicit lazy val emailSignUpAtomEncoder: Encoder[EmailSignUpAtom] = deriveEncoder

  // atoms/explainer.thrift
  implicit lazy val displayTypeEncoder: Encoder[DisplayType] = deriveEncoder
  implicit lazy val explainerAtomEncoder: Encoder[ExplainerAtom] = deriveEncoder

  // atoms/guide.thrift
  implicit lazy val guideItemEncoder: Encoder[GuideItem] = deriveEncoder
  implicit lazy val guideAtomEncoder: Encoder[GuideAtom] = deriveEncoder

  // atoms/interactive.thrift
  implicit lazy val interactiveAtomEncoder: Encoder[InteractiveAtom] = deriveEncoder

  // atoms/media.thrift
  implicit lazy val platformEncoder: Encoder[Platform] = deriveEncoder
  implicit lazy val assetTypeEncoder: Encoder[AssetType] = deriveEncoder
  implicit lazy val categoryEncoder: Encoder[Category] = deriveEncoder
  implicit lazy val privacyStatusEncoder: Encoder[PrivacyStatus] = deriveEncoder
  implicit lazy val videoPlayerFormatEncoder: Encoder[VideoPlayerFormat] = deriveEncoder
  implicit lazy val mediaAssetEncoder: Encoder[MediaAsset] = deriveEncoder
  implicit lazy val plutoDataEncoder: Encoder[PlutoData] = deriveEncoder
  implicit lazy val iconikDataEncoder: Encoder[IconikData] = deriveEncoder
  implicit lazy val youtubeDataEncoder: Encoder[YoutubeData] = deriveEncoder
  implicit lazy val selfHostDataEncoder: Encoder[SelfHostData] = deriveEncoder
  implicit lazy val metadataEncoder: Encoder[Metadata] = deriveEncoder
  implicit lazy val mediaAtomEncoder: Encoder[MediaAtom] = deriveEncoder

  // atoms/profile.thrift
  implicit lazy val profileItemEncoder: Encoder[ProfileItem] = deriveEncoder
  implicit lazy val profileAtomEncoder: Encoder[ProfileAtom] = deriveEncoder

  // atoms/qanda.thrift
  implicit lazy val qAndAItemEncoder: Encoder[QAndAItem] = deriveEncoder
  implicit lazy val qAndAAtomEncoder: Encoder[QAndAAtom] = deriveEncoder

  // atoms/quiz.thrift
  implicit lazy val resultGroupEncoder: Encoder[ResultGroup] = deriveEncoder
  implicit lazy val quizAssetEncoder: Encoder[QuizAsset] = deriveEncoder
  implicit lazy val answerEncoder: Encoder[Answer] = deriveEncoder
  implicit lazy val resultBucketEncoder: Encoder[ResultBucket] = deriveEncoder
  implicit lazy val resultBucketsEncoder: Encoder[ResultBuckets] = deriveEncoder
  implicit lazy val questionEncoder: Encoder[Question] = deriveEncoder
  implicit lazy val resultGroupsEncoder: Encoder[ResultGroups] = deriveEncoder
  implicit lazy val quizContentEncoder: Encoder[QuizContent] = deriveEncoder
  implicit lazy val quizAtomEncoder: Encoder[QuizAtom] = deriveEncoder

  // atoms/review.thrift
  implicit lazy val reviewTypeEncoder: Encoder[ReviewType] = deriveEncoder
  implicit lazy val ratingEncoder: Encoder[Rating] = deriveEncoder
  implicit lazy val reviewAtomEncoder: Encoder[ReviewAtom] = deriveEncoder

  // atoms/shared.thrift
  implicit lazy val userEncoder: Encoder[User] = deriveEncoder
  implicit lazy val changeRecordEncoder: Encoder[ChangeRecord] = deriveEncoder
  implicit lazy val sectionEncoder: Encoder[Section] = deriveEncoder
  implicit lazy val tagEncoder: Encoder[Tag] = deriveEncoder
  implicit lazy val newspaperEncoder: Encoder[Newspaper] = deriveEncoder
  implicit lazy val tagUsageEncoder: Encoder[TagUsage] = deriveEncoder
  implicit lazy val referenceEncoder: Encoder[Reference] = deriveEncoder
  implicit lazy val taxonomyEncoder: Encoder[Taxonomy] = deriveEncoder
  implicit lazy val imageAssetDimensionsEncoder: Encoder[ImageAssetDimensions] = deriveEncoder
  implicit lazy val imageAssetEncoder: Encoder[ImageAsset] = deriveEncoder
  implicit lazy val imageEncoder: Encoder[Image] = deriveEncoder
  implicit lazy val emailProviderEncoder: Encoder[EmailProvider] = deriveEncoder
  implicit lazy val notificationProvidersEncoder: Encoder[NotificationProviders] = deriveEncoder

  // atoms/timeline.thrift
  implicit lazy val timelineItemEncoder: Encoder[TimelineItem] = deriveEncoder
  implicit lazy val timelineAtomEncoder: Encoder[TimelineAtom] = deriveEncoder

  // contentatom.thrift
  implicit lazy val atomTypeEncoder: Encoder[AtomType] = deriveEncoder
  implicit lazy val atomDataEncoder: Encoder[AtomData] = deriveEncoder
  implicit lazy val contentChangeDetailsEncoder: Encoder[ContentChangeDetails] = deriveEncoder
  implicit lazy val flagsEncoder: Encoder[Flags] = deriveEncoder
  implicit lazy val atomEncoder: Encoder[Atom] = deriveEncoder
  implicit lazy val eventTypeEncoder: Encoder[EventType] = deriveEncoder
  implicit lazy val contentAtomEventEncoder: Encoder[ContentAtomEvent] = deriveEncoder
}
