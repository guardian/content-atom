include "contentatom.thrift"

typedef string ContentAtomID

enum EventType { PUBLISH, UPDATE, TAKEDOWN }

enum AtomType { TENFOUR_QUIZ_BUILDER }

struct ContentAtomEvent {

  /* this opaque identifier should uniquely identify this content atom
   * across all content-atoms of any type (an alternative model might
   * be to have the `type+id` be unique) */

   1: required ContentAtomID id

   2: required AtomType atomType

   3: required EventType eventType
   
   4: required contentatom.ContentAtom data
 }
