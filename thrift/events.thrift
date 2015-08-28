include "contentatom.thrift"

typedef string ContentAtomID

enum EventType { PUBLISH, UPDATE, TAKEDOWN }

struct ContentAtomEvent {

  /* this opaque identifier should uniquely identify this content atom
   * across all content-atoms of any type (an alternative model might
   * be to have the `type+id` be unique) */

   1: required ContentAtomID id

   2: required string atomType

   3: required EventType eventType
   
   4: required contentatom.ContentAtom data
 }
