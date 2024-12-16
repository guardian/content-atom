namespace * contentatom.interactive
namespace java com.gu.contentatom.thrift.atom.interactive
#@namespace scala com.gu.contentatom.thrift.atom.interactive
#@namespace typescript _at_guardian.content_atom_model.interactive

union AnyVal {
    1: bool boolVal
    2: byte byteVal
    3: i16 i16Val
    4: i32 i32Val
    5: i64 i64Val
    6: double doubleVal
    7: string stringVal
    8: list<AnyVal> listVal
    9: set<AnyVal> setVal
    10: map<string, AnyVal> mapVal
}

struct CustomField {
  1: required string fieldName
  2: required string fieldType
  3: required AnyVal defaultValue
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
