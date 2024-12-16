namespace * contentatom.interactive
namespace java com.gu.contentatom.thrift.atom.interactive
#@namespace scala com.gu.contentatom.thrift.atom.interactive
#@namespace typescript _at_guardian.content_atom_model.interactive

union AnyValue {
    1: bool boolValue
    2: byte byteValue
    3: i16 i16Value
    4: i32 i32Value
    5: i64 i64Value
    6: double doubleValue
    7: string stringValue
    8: list<AnyValue> listValue
    9: set<AnyValue> setValue
    10: map<string, AnyValue> mapValue
}

struct CustomField {
  1: required string fieldName
  2: required string fieldType
  3: required AnyValue defaultValue
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
  8: optional list<CustomField> customFields = []
}
