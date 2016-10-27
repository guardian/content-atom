namespace * contentatom.recipe
namespace java com.gu.contentatom.thrift.atom.recipe
#@namespace scala com.gu.contentatom.thrift.atom.recipe

struct RecipeAtom {
  /* the unique ID will be stored in the `atom` data*/
  1: required string title
  2: required Tags tags 
  3: required Time time
  4: optional Serves serves
  5: required list<IngredientsList> ingredientsLists
  6: required list<string> steps
  7: required list<string> credits
}

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
  1: required string type
  2: required i16 from
  3: required i16 to
}

struct IngredientsList {
    1: optional string title
    2: required list<Ingredient> ingredients
}

struct Ingredient {
    1: required string item
    2: optional string comment
    3: required double quantity
    4: optional string unit
}