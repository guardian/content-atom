namespace * contentatom.emailsignup
namespace java com.gu.contentatom.thrift.atom.emailsignup
#@namespace scala com.gu.contentatom.thrift.atom.emailsignup

include "shared.thrift"

struct EmailSignUpAtom {
  // email list name
  1: required string emailListName

  // Main text to be displayed to user asking for sign up action,
  // e.g. "Sign up to get emials for this series, Guns and lies in America"
  2: required string formTitle

  // Additional text describing what will happen on sign up,
  // e.g. "You'll get an email each time we release a part of our investigation."
  3: required string formDescription

  // The exact target ID for the email list
  4: required string emailListId
}
