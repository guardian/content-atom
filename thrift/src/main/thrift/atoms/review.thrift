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

struct Geolocation {
  1: required double lat
  2: required double lon
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

struct Price {
  //ISO 4217 currency code
  1: required string currency
  2: required double value
}

struct RestaurantReview {
  1: required string restaurantName
  2: optional string approximateLocation
  3: optional string webAddress
  4: optional Address address
  5: optional Geolocation geolocation
}

struct GameReview {
  1: required string title
  2: optional string publisher
  3: required list<string> platforms
  4: optional Price price
  5: optional i32 pegiRating
  6: optional string genre
}

struct ReviewAtom {
  1: required ReviewType reviewType
  2: required string reviewer
  3: required Rating rating
  4: optional string reviewSnippet
  5: optional RestaurantReview restaurantReview
  6: optional GameReview gameReview
}
