namespace * contentatom.interactive
namespace java com.gu.contentatom.thrift.atom.interactive
#@namespace scala com.gu.contentatom.thrift.atom.interactive
#@namespace typescript _at_guardian.content_atom_model.interactive

struct InteractiveAtom {
  /* the unique ID will be stored in the `atom` data*/
  1: required string type
  2: required string title
  3: required string css
  4: required string html
  5: optional string mainJS
  6: optional string docData
}
