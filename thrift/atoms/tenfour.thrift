namespace * tenfour

include "../shared.thrift"

/** date times are reprsented as i64 - epoch millis */
typedef i64 DateTime

struct TenfourQuizBuilderAtom {
  // do we need to store the ID, seeing as it is replicated(?) in the
  // content-atom wrapping?
  1  : required string id
  2  : required string title
  3  : required string updatedBy
  4  : required DateTime updatedAt
  5  : required DateTime createdAt
  6  : required string createdBy
  7  : required bool published
  8  : required string quizType
  9  : optional i16 defaultColumns
  10 : required ItemContent content
}

// will vary depending on item's type
union ItemContent {
  1: QuizContent quiz
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
  3: required bool correct
  4: optional string revealText
}

struct Asset {
  1: required string type
  /* what type is this? currently assuming opaque json */
  2: required shared.OpaqueJson data
}
