export default function TopKeywordTable({ rows }) {
    if (!rows || rows.length === 0) {
        return (
            <div style={{ padding: 12, border: '1px solid #ddd', borderRadius: 8 }}>
                데이터가 없습니다.
            </div>
        )
    }

    return (
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
                <tr>
                    <th style={th}>#</th>
                    <th style={th}>Keyword</th>
                    <th style={th}>Count</th>
                    <th style={th}>Keyword ID</th>
                </tr>
            </thead>
            <tbody>
                {rows.map((r, idx) => (
                    <tr key={`${r.keywordId}-${idx}`}>
                        <td style={td}>{idx + 1}</td>
                        <td style={td}>{r.keywordName}</td>
                        <td style={td}>{r.count}</td>
                        <td style={td}>{r.keywordId}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

const th = {
    textAlign: 'left',
    padding: '10px 8px',
    borderBottom: '1px solid #ddd',
    fontWeight: 700,
}

const td = {
    padding: '10px 8px',
    borderBottom: '1px solid #eee',
}