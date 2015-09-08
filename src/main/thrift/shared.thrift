namespace java com.gu.contentatom.thrift

/** date times are reprsented as i64 - epoch millis */
typedef i64 DateTime

typedef string OpaqueJson

struct ChangeRecord {

    /** when the change occured */
    1: required DateTime date;

    /** the user that performed the change */
    2: optional User user;
}

struct User {

    1: required string email;

    2: optional string firstName;

    3: optional string lastName;
}
