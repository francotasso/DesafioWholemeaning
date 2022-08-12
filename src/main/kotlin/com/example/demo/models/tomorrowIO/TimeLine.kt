import java.time.LocalDate

data class TimeLine(
    val endTime: LocalDate,
    val timestep: String,
    val intervals: List<Interval>
)