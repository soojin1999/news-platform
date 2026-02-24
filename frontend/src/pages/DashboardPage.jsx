import { useEffect, useMemo, useState } from 'react'
import { fetchTopKeywords } from '../api/dashboard'
import {
    nowForDatetimeLocalHour,
    normalizeToHourInput,
    toServerHourBucket,
} from '../utils/time'

export default function DashboardPage() {
    const [time, setTime] = useState(nowForDatetimeLocalHour())
    const [rows, setRows] = useState([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(null)

    const serverTime = useMemo(() => toServerHourBucket(time), [time])

    async function load() {
        setLoading(true)
        setError(null)
        try {
            const data = await fetchTopKeywords({ time: serverTime })
            setRows(data)
        } catch (e) {
            setError(e?.response?.data?.message || e.message || '요청 실패')
            setRows([])
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        load()
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    return (
        <div className="min-h-screen bg-slate-50">
            <div className="mx-auto max-w-5xl px-4 py-10">
                <div className="flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
                    <div>
                        <h1 className="text-2xl font-bold tracking-tight text-slate-900">
                            Hot Topics Dashboard
                        </h1>
                        <p className="text-sm text-slate-600">
                            뉴스에서 추출한 키워드 집계를 기반으로 현재 버킷의 핫 토픽을 보여줍니다.
                        </p>
                    </div>

                    <div className="text-xs text-slate-500">
                        요청 버킷: <span className="font-mono">{serverTime}</span>
                    </div>
                </div>

                <div className="mt-6 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
                    <div className="flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
                        <div className="flex flex-col gap-2">
                            <label className="text-xs font-medium text-slate-600">
                                Time (hour)
                            </label>
                            <input
                                type="datetime-local"
                                value={time}
                                step={3600}
                                onChange={(e) => setTime(normalizeToHourInput(e.target.value))}
                                className="w-full rounded-xl border border-slate-300 bg-white px-3 py-2 text-sm text-slate-900 shadow-sm outline-none focus:border-slate-400 focus:ring-2 focus:ring-slate-200 sm:w-72"
                            />
                            <p className="text-xs text-slate-500">
                                시간 단위로만 선택되며, 서버에는 항상{' '}
                                <span className="font-mono">HH:00:00</span>으로 전송됩니다.
                            </p>
                        </div>

                        <div className="flex gap-2">
                            <button
                                onClick={load}
                                disabled={loading}
                                className="inline-flex items-center justify-center rounded-xl bg-slate-900 px-4 py-2 text-sm font-semibold text-white shadow-sm hover:bg-slate-800 disabled:cursor-not-allowed disabled:opacity-60"
                            >
                                {loading ? 'Loading...' : 'Fetch'}
                            </button>
                            <button
                                onClick={() => {
                                    setTime(nowForDatetimeLocalHour())
                                    setTimeout(load, 0)
                                }}
                                className="inline-flex items-center justify-center rounded-xl border border-slate-300 bg-white px-4 py-2 text-sm font-semibold text-slate-700 shadow-sm hover:bg-slate-50"
                            >
                                Now
                            </button>
                        </div>
                    </div>

                    {error && (
                        <div className="mt-4 rounded-xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm text-rose-700">
                            <span className="font-semibold">Error:</span> {String(error)}
                        </div>
                    )}
                </div>

                <div className="mt-6 rounded-2xl border border-slate-200 bg-white shadow-sm">
                    <div className="flex items-center justify-between border-b border-slate-200 px-5 py-4">
                        <h2 className="text-sm font-semibold text-slate-900">
                            Top Keywords
                        </h2>
                        <div className="text-xs text-slate-500">{rows?.length ?? 0} items</div>
                    </div>

                    {loading ? (
                        <div className="px-5 py-10 text-center text-sm text-slate-500">
                            데이터를 불러오는 중...
                        </div>
                    ) : rows.length === 0 ? (
                        <div className="px-5 py-10 text-center text-sm text-slate-500">
                            해당 시간 버킷에 데이터가 없습니다.
                        </div>
                    ) : (
                        <div className="overflow-x-auto">
                            <table className="min-w-full">
                                <thead className="bg-slate-50">
                                    <tr>
                                        <th className="px-5 py-3 text-left text-xs font-semibold text-slate-600">
                                            #
                                        </th>
                                        <th className="px-5 py-3 text-left text-xs font-semibold text-slate-600">
                                            Keyword
                                        </th>
                                        <th className="px-5 py-3 text-left text-xs font-semibold text-slate-600">
                                            Count
                                        </th>
                                        <th className="px-5 py-3 text-left text-xs font-semibold text-slate-600">
                                            Keyword ID
                                        </th>
                                    </tr>
                                </thead>
                                <tbody className="divide-y divide-slate-100">
                                    {rows.map((r, idx) => (
                                        <tr key={`${r.keywordId}-${idx}`} className="hover:bg-slate-50">
                                            <td className="px-5 py-3 text-sm text-slate-700">{idx + 1}</td>
                                            <td className="px-5 py-3 text-sm font-medium text-slate-900">
                                                {r.keywordName}
                                            </td>
                                            <td className="px-5 py-3 text-sm text-slate-700">{r.count}</td>
                                            <td className="px-5 py-3 text-sm font-mono text-slate-600">
                                                {r.keywordId}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>

                {/*
          ✅ NOTE(개발자 메모):
          다음 단계 추천: 키워드 클릭 → 트렌드(시간대별 count) API로 라인차트까지 붙이면 “핫 토픽”이 더 설득력 있어짐.
        */}
            </div>
        </div>
    )
}