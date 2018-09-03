namespace * contentatom.chart
namespace java com.gu.contentatom.thrift.atom.chart

enum ChartType {
    BAR = 0
    BARGROUP = 1
    BARGROUPSTACK = 2
    BARGROUPSTACK100 = 3
    BAR100 = 4
    BROKENBAR100 = 5
    COL = 6
    COLGROUP = 7
    COLGROUPSTACK = 8
    COLGROUPSTACK100 = 9
    LINEDISCRETE = 10
    LINECONTINUE = 11
    PLOTDOT = 12
    PLOTSCATTER = 13
    ONBARDIFFARROW = 14
    ONBARDIFFDOTS = 15
    ONBARTICKS = 16
    SLOPEGRAPH = 17
}

struct Range {
    1: required double min
    2: required double max
}

struct DisplaySettings {
    1: required bool showHeadline = true
    2: required bool showSource = true
    3: optional bool showStandfirst
    4: optional bool showLegend
}

struct Furniture {
    1: required string headline
    2: required string source
    3: optional string standfirst
}

enum RowType {
    STRING = 0,
    DATE = 1
}

struct RowHeader {
    1: required RowType rowType
    2: required string value
}

struct Row {
    1: required RowHeader rowHeader
    2: required list<double> values
}

struct TabularData {
    1: required list<string> columnHeaders
    2: required list<Row> rows
}

struct SeriesColour {
    1: required i32 index // a column or row
    2: required string hexCode
}

struct Axis {
    1: required list<double> scale
    2: required Range range
}

struct ChartAtom {
    1: required ChartType chartType
    2: required Furniture furniture
    3: required TabularData tabularData
    4: required list<SeriesColour> seriesColour
    5: required DisplaySettings displaySettings
    6: optional list<i32> hiddenColumns
    7: optional list<i32> hiddenRows
    8: optional Axis xAxis
    9: optional Axis yAxis
}
