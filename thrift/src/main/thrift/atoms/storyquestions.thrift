namespace * contentatom.storyquestions
namespace java com.gu.contentatom.thrift.atom.storyquestions
#@namespace scala com.gu.contentatom.thrift.atom.storyquestions

/*
 * Determines what the questions are linked to. This is fundamentally to work around the fact
 * that we do not have the concept of a story yet.
 */
enum RelatedStoryLinkType {
  TAG = 0,
  STORY = 1,
}

struct Question {
  1: required string questionId
  2: required string questionText
}

struct QuestionSet {
  1: required string questionSetId
  2: required string questionSetTitle
  3: required list<Question> questions
}

struct StoryQuestionsAtom {
  1: required string relatedStoryId
  2: required RelatedStoryLinkType relatedStoryLinkType
  3: required string title
  4: optional list<QuestionSet> editorialQuestions
  5: optional list<QuestionSet> userQuestions
  6: optional string emailListId
}
