package per.scrumgun.core.mapper

interface Mapper<in From, out To> {
	fun map(from: From): To
}
