namespace * contentatom.emailsignup
namespace java com.gu.contentatom.thrift.atom.emailsignup
#@namespace scala com.gu.contentatom.thrift.atom.emailsignup

include "shared.thrift"

struct EmailSignUpAtom {
  // email list name
  1: required string emailListName

  // Text to be displayed to users to describe what they are signing up for
  // e.g. "Sign up to the daily Business Today email"
  2: required string text

  // The exact target ID for the email list
  3: required string emailListId
}
