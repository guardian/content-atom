namespace * contentatom.multimediaslideshow
namespace java com.gu.contentatom.thrift.atom.multimediaslideshow
#@namespace scala com.gu.contentatom.thrift.atom.multimediaslideshow
#@namespace typescript _at_guardian.content_atom_model.multimediaslideshow

include "shared.thrift"

/**
* A reference to an existing media (video) atom.
*
* Used when a slide displays a video rather than a still image. The media
* atom itself is stored separately and looked up via its atom id.
*/
struct MediaReference {
  /** The id of the media atom to be displayed on this slide. */
  1: required string mediaAtomId
}

/**
* The content shown on an individual slide. A slide is either a still image
* or a reference to a media (video) atom.
*/
union SlideContent {
  1: shared.Image image
  2: MediaReference mediaAtom
}

/**
* A single slide within a multimedia slideshow.
*/
struct Slide {
  /** The image or media atom displayed on this slide. */
  1: required SlideContent content

  /** Free text caption describing the slide. */
  2: optional string caption

  /** Short text label for the slide. */
  3: optional string label

  /** Picture or video credit text. */
  4: optional string credit
}

/**
* A multimedia slideshow made up of an ordered list of slides. Each slide can
* be a still image or a media (video) atom.
*/
struct MultimediaSlideshowAtom {
  /** The ordered list of slides that make up the slideshow. */
  1: required list<Slide> slides

  /** Optional title for the slideshow. */
  2: optional string title
}
