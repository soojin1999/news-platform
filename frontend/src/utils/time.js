// 현재 시간을 "시간 단위" datetime-local input에 넣기 좋은 형태로 (분 00 고정)
export function nowForDatetimeLocalHour() {
    const d = new Date()
    d.setMinutes(0, 0, 0) // 분/초/ms 0
    return toDatetimeLocalHourString(d) // yyyy-MM-ddTHH:00
}

function toDatetimeLocalHourString(d) {
    const pad = (n) => String(n).padStart(2, '0')
    const yyyy = d.getFullYear()
    const MM = pad(d.getMonth() + 1)
    const dd = pad(d.getDate())
    const HH = pad(d.getHours())
    return `${yyyy}-${MM}-${dd}T${HH}:00`
}

// datetime-local 값(yyyy-MM-ddTHH:mm)을 받아서 "분 00"으로 정규화 (input용)
export function normalizeToHourInput(value) {
    // value: "2026-02-24T17:30" -> "2026-02-24T17:00"
    if (!value) return value
    const [date, time] = value.split('T')
    const hh = (time || '00:00').slice(0, 2)
    return `${date}T${hh}:00`
}

// 서버 전송용: 항상 초 포함, 분/초 00 고정
export function toServerHourBucket(value) {
    // value: "2026-02-24T17:00" -> "2026-02-24T17:00:00"
    // value: "2026-02-24T17:30" -> "2026-02-24T17:00:00" (보정)
    const normalized = normalizeToHourInput(value)
    return `${normalized}:00`
}