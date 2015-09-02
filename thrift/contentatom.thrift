include "atoms/quiz.thrift"
include "shared.thrift"

typedef string ContentAtomID

enum AtomType { QUIZ }

struct Atom {
  1: required ContentAtomID id
  2: required AtomType atomType
  3: required list<string> labels // required, but may be empty
  4: required string defaultBody
  5: required AtomData data       // the atom payload
 }

union AtomData {
  1: quiz.QuizAtom quiz
}

enum EventType { PUBLISH, UPDATE, TAKEDOWN }

struct ContentAtomEvent {
  1: required Atom atom
  2: required EventType eventType
  3: required shared.DateTime eventCreationTime
}
