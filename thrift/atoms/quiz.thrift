namespace * contentatom.quiz

include "../shared.thrift"

struct QuizAtom {
  // do we need to store the ID, seeing as it is replicated(?) in the
  // content-atom wrapping?
  1  : required string id
  2  : required string title
  7  : required bool published
  8  : required string quizType
  9  : optional i16 defaultColumns
  10 : required QuizContent content
}

struct QuizContent {
  1: required list<Question> questions
  2: required ResultGroups resultGroups
}

struct ResultGroups {
  1: required list<ResultGroup> groups
  2: required bool revealAtEnd
}

struct ResultGroup {
  1: required string title
  2: required string share
  3: required i16 minScore
}

struct Question {
  1: required string questionText
  2: required list<Asset> assets
  3: required list<Answer> answers
}

struct Answer {
  1: required string answerText
  2: required list<Asset> assets
  3: required i16 weight
  4: optional string revealText
}

struct Asset {
  1: required string type
  /* what type is this? currently assuming opaque json */
  2: required shared.OpaqueJson data
}
