typedef string ContentAtomID

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
   
   5: string data // a string contain an opaque, JSON-encoded, data blob
 }
