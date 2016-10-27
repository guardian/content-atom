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
  6: required list<String> steps
  7: required list<String> credits 
}

struct Tags {
 1: required list<String> cuisine
 2: required list<String> category
 3: required list<String> celebration
 4: required list<String> dietary
}

struct Time {
  1: optional short preparation
  2: optional short cooking
}

struct Serves {
  1: required string type
  2: required short from 
  3: required short to
}

struct IngredientsList {
    1: optional string title
    2: required list<Ingredient> ingredients
}

struct Ingredient {
    1: required string item
    2: optional string comment
    3: required float quantity
    4: optional string unit
}