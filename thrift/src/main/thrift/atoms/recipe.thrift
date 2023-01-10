namespace * contentatom.recipe
namespace java com.gu.contentatom.thrift.atom.recipe
#@namespace scala com.gu.contentatom.thrift.atom.recipe
#@namespace typescript _at_guardian.content_atom_model.recipe

include "shared.thrift"

struct Tags {
  1: required list<string> cuisine
  2: required list<string> category
  3: required list<string> celebration
}

struct Time {
  1: optional i16 preparation
  2: optional i16 cooking
}

struct Serves {
  1: required string type
  2: required i16 from
  3: required i16 to
  4: optional string unit
}

struct Range {
  1: required i16 from
  2: required i16 to
}

struct Ingredient {
  1: required string item
  2: optional string comment
  3: required Range quantityRange
  4: optional string unit
  5: optional string dietaryInfo
  6: optional Range caloryRange
}

struct IngredientsList {
  1: optional string title
  2: required list<Ingredient> ingredients
}

struct RecipeStep {
    1: required string name
    2: required string text
    3: optional shared.Image image
    4: optional string link
}

struct RecipeAtom {
  1: required string title
  2: required string description
  3: required list<string> authorOrAuthors
  4: required list<shared.Image> leadImageOrImages
  5: required Tags tags
  6: required Time time
  7: required Serves serves
  8: required list<IngredientsList> ingredientsLists
  9: required list<RecipeStep> steps
  10: optional string canonicalArticleId
}
