namespace * contentatom.review
namespace java com.gu.contentatom.thrift.atom.review
#@namespace scala com.gu.contentatom.thrift.atom.review

enum ReviewType {
  RESTAURANT = 1
}

struct Rating {
  1: required i16 maxRating
  2: required i16 actualRating
  3: required i16 minRating
}

struct Location {
  1: required double lat
  2: required double lng
}

struct Address {
  1: optional string formattedAddress
  2: optional i16 streetNumber
  3: optional string streetName
  4: optional string neighbourhood
  5: optional string postTown
  6: optional string locality
  7: optional string country
  8: optional string administrativeAreaLevelOne
  9: optional string administrativeAreaLevelTwo
  10: optional string postCode
}

struct RestaurantReview {
  1: required string restaurantName
  2: optional string approximateLocation
  3: optional string webAddress
  4: optional Address address
  5: optional Location location
}

union ReviewItem {
  1: RestaurantReview restaurantReview
}

struct ReviewAtom {
  1: required ReviewItem reviewItem
  2: required ReviewType reviewType
  3: required string reviewer
  4: optional Rating rating
  5: optional string reviewSnippet
}