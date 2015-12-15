namespace * contentatom.poll
namespace java com.gu.contentatom.thrift.atom.poll
# for some reason scrooge (the version we're using at least) overrides 'java'
# with '*', so we need to add the scala namespace. Apache Thrift will reject
# this so they have allowed this special format which appears as a comment to
# Thrift.
#@namespace scala com.gu.contentatom.thrift.atom.poll

include "../shared.thrift"

struct PollOption {
  1 : required string id
  2 : required string optionText
  3 : required i32 count
  4 : required double percentage
}


struct PollAtom {
  1 : required string id
  2 : required string questionText
  3 : required list<PollOption> options
  4 : required i32 submissionCount
  5 : optional shared.DateTime closeDate
}
