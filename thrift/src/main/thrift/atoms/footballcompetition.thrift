namespace * contentatom.footballcompetition
namespace java com.gu.contentatom.thrift.atom.footballcompetition
#@namespace scala com.gu.contentatom.thrift.atom.footballcompetition
#@namespace typescript _at_guardian.content_atom_model.footballcompetition

enum FootballCompetitionComponentType {
  MATCHDAY = 0,
}


struct FootballCompetitionAtom {
  1: required string competitionId,
  2: required FootballCompetitionComponentType componentType,
//  3: optional FootballCompetitionComponentData data  // This can be used for any specific data for future components, if needed 
}

// If we ever need to have specific data fields for competition components, 
// we can define them here. 
/*
union FootballCompetitionComponentData {
  1:  MatchDayComponentData matchDayData
}
*/