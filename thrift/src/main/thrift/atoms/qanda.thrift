namespace * contentatom.qanda
namespace java com.gu.contentatom.thrift.atom.qanda
#@namespace scala com.gu.contentatom.thrift.atom.qanda

include "../shared.thrift"

struct QAndAAtom {
  1: optional string typeLabel
  2: optional list<string> tags
  3: optional Image eventImage
  4: required list<QAndAItem> items
  // Ask Nathan: should we link to a StoryQuestions instead?
  5: optional string sidenote
}

struct QAndAItem {
  1: optional string title
  2: required string body
}
