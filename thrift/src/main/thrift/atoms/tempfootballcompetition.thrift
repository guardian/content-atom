namespace * contentatom.tempfootballcompetition
namespace java com.gu.contentatom.thrift.atom.tempfootballcompetition
#@namespace scala com.gu.contentatom.thrift.atom.tempfootballcompetition
#@namespace typescript _at_guardian.content_atom_model.tempfootballcompetition

enum TempFootballCompetitionComponentType {
  MATCHDAY = 0,
}


struct TempFootballCompetitionAtom {
  1: required string competitionId,
  2: required TempFootballCompetitionComponentType componentType,
//  3: optional TempFootballCompetitionComponentData data  // This can be used for any specific data for future components, if needed 
}

// If we ever need to have specific data fields for competition components, 
// we can define them here. 
/*
union TempFootballCompetitionComponentData {
  1:  MatchDayComponentData matchDayData
}
*/