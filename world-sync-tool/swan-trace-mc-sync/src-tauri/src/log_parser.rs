/// 判断是否为 rsync 的 itemize 行（表示一个文件/目录变更）。
///
/// 例：
/// ```text
/// >f+++++++++ path/to/file.txt
/// <f.st...... path/to/file.txt
/// .d..t...... somedir/
/// cd+++++++++ path/to/file.txt
/// *deleting   path/to/file.txt
/// ```
pub fn is_itemize_line(line: &str) -> bool {
    let bytes = line.as_bytes();
    if bytes.is_empty() {
        return false;
    }

    if line.starts_with("*deleting") {
        return true;
    }

    let first = bytes[0];
    if !matches!(first, b'>' | b'<' | b'c' | b'h' | b'.') {
        return false;
    }

    // itemize 字符串固定 11 个字符，后面紧跟空格和路径
    bytes.len() > 11 && bytes[11] == b' '
}

/// 判断是否为 rsync 的传输进度行。
///
/// 例：
/// ```text
///      32,768  31%    1.18MB/s    0:00:00 (xfr#1, to-chk=1/2)
///  12,345,678 100%    4.56MB/s    0:00:02 (xfr#3, to-chk=57/100)
/// ```
pub fn is_progress_line(line: &str) -> bool {
    if !line.contains('%') {
        return false;
    }

    line.contains("/s")
        || line.contains("xfr#")
        || line.contains("to-chk")
        || line.contains("ir-chk")
}

/// 从进度行里提取百分比数字。
pub fn extract_percent(line: &str) -> Option<u32> {
    for part in line.split_whitespace() {
        if let Some(stripped) = part.strip_suffix('%') {
            if let Ok(value) = stripped.parse::<u32>() {
                return Some(value);
            }
        }
    }
    None
}

/// 判断是否为应忽略的 rsync 噪音行。
pub fn is_ignored_rsync_line(line: &str) -> bool {
    const IGNORED: &[&str] = &[
        "receiving incremental file list",
        "sending incremental file list",
        "sent ",
        "received ",
        "total size is ",
        "speedup is ",
    ];

    IGNORED.iter().any(|prefix| line.starts_with(prefix))
}