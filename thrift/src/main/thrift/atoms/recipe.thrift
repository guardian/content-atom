namespace * contentatom.recipe
namespace java com.gu.contentatom.thrift.atom.recipe
#@namespace scala com.gu.contentatom.thrift.atom.recipe
#@namespace typescript _at_guardian.content_atom_model.recipe

include "shared.thrift"

struct Tags {
  1: required list<string> cuisine
  2: required list<string> category
  3: required list<string> celebration
  4: required list<string> dietary
}

struct Time {
  1: optional i16 preparation
  2: optional i16 cooking
}

struct Serves {
  /* note that `from` (field 2) has been renamed `minimum` due to `from` becoming a reserved word in scrooge */
  /* and that `to` (field 3) is now `maximum` to keep it semantically in line with `from` becoming `minimum` */
  1: required string type
  2: required i16 minimum
  3: required i16 maximum
  4: optional string unit
}

struct Range {
  /* note that `from` (field 1) has been renamed `minimum` due to `from` becoming a reserved word in scrooge */
  /* and that `to` (field 2) is now `maximum` to keep it semantically in line with `from` becoming `minimum` */
  1: required i16 minimum
  2: required i16 maximum
}

struct Ingredient {
  1: required string item
  2: optional string comment
  3: optional double quantity
  4: optional Range quantityRange
  5: optional string unit
}

struct IngredientsList {
  1: optional string title
  2: required list<Ingredient> ingredients
}

struct RecipeAtom {
  /* the unique ID will be stored in the `atom` data */
  1: required string title
  2: required Tags tags 
  3: required Time time
  4: optional Serves serves
  5: required list<IngredientsList> ingredientsLists
  6: required list<string> steps
  7: required list<string> credits
  8: required list<shared.Image> images
  9: optional string sourceArticleId
}