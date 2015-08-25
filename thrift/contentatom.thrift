namespace * contentatom

/* each type of content-atom will have its definitions in a different
 * folder in atoms/ */

include "atoms/quiz.thrift"
include "atoms/note.thrift"
 
typedef string ContentAtomID

union AtomData {
  1: quiz.QuizType quiz
  2: note.NoteType note
}

enum EventType { PUBLISH, UPDATE, TAKEDOWN }

struct ContentAtomEvent {

  /* this opaque identifier should uniquely identify this content atom
   * across all content-atoms of any type (an alternative model might
   * be to have the `type+id` be unique) */

   1: ContentAtomID id

   // this is a canonical place from which the current version of the
   //content atom's data can be downloaded.
   2: string url

   3: string atomType

   4: EventType eventType
   
   5: AtomData data
 }
