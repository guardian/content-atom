namespace * contentatom.audio
namespace java com.gu.contentatom.thrift.atom.audio
#@namespace scala com.gu.contentatom.thrift.atom.audio

include "../shared.thrift"

struct AudioAtom {
  // Most probably the title of the podcast, but can be a generic
  // term if necessary
  1: required string kicker

  // Cover art for the podcast/audio. Can either be the podcast
  // album art of a picture chosen specifically for this episode.
  2: required string coverUrl

  // MP3 audio track. We don't support other formats
  3: required string trackUrl

  4: required i32 duration
}
