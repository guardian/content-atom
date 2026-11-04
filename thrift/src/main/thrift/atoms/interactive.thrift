namespace * contentatom.interactive
namespace java com.gu.contentatom.thrift.atom.interactive
#@namespace scala com.gu.contentatom.thrift.atom.interactive
#@namespace typescript _at_guardian.content_atom_model.interactive

enum FieldType {
  TEXT = 0
}

struct CustomDataDefinition {
  1: required string fieldId
  2: required string fieldName
  3: required FieldType fieldType
  4: required string fieldDescription
  5: required string defaultValue
}

struct InteractiveAtom {
  /* the unique ID will be stored in the `atom` data*/
  1: required string type
  2: required string title
  3: required string css
  4: required string html
  5: optional string mainJS
  6: optional string docData

  /*
  When an interactive is within an amp-iframe,
  a placeholder field is required to for the iframe to be displayed at the top of the AMP.
  See here: https://amp.dev/documentation/components/amp-iframe/ for more information.
   */
  7: optional string placeholderUrl
  8: optional list<CustomDataDefinition> customDataDefinitions = []
}
