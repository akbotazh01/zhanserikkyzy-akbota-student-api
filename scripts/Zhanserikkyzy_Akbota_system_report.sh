#!/bin/bash
# =====================================================================
# System report script
# Student: Akbota Zhanserikkyzy | ID: 37106 | Group: IT2-2308
# Works on macOS (Darwin) and Linux.
# Output is shown on screen AND saved to the personal log file.
# =====================================================================

# Stop on use of undefined variables and on errors in pipelines
set -u
set -o pipefail

STUDENT_NAME="Akbota Zhanserikkyzy"
STUDENT_ID="37106"
STUDENT_GROUP="IT2-2308"

# Project directory = parent directory of the folder where this script lives
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
LOG_DIR="${PROJECT_DIR}/logs"
LOG_FILE="${LOG_DIR}/Zhanserikkyzy_Akbota_system_report.log"

mkdir -p "$LOG_DIR" || { echo "Cannot create log directory: $LOG_DIR" >&2; exit 1; }

OS_NAME="$(uname -s)"

report() {
    echo "=============================================="
    echo " SYSTEM REPORT"
    echo "=============================================="
    echo "Student full name : ${STUDENT_NAME}"
    echo "Student ID        : ${STUDENT_ID}"
    echo "Group             : ${STUDENT_GROUP}"
    echo "Date and time     : $(date '+%Y-%m-%d %H:%M:%S %Z')"
    echo "Current username  : $(whoami)"
    echo "Hostname          : $(hostname)"
    echo

    echo "--- Operating system and kernel ---"
    if [ "$OS_NAME" = "Darwin" ]; then
        echo "OS                : $(sw_vers -productName) $(sw_vers -productVersion) (build $(sw_vers -buildVersion))"
    elif [ -r /etc/os-release ]; then
        # shellcheck disable=SC1091
        . /etc/os-release
        echo "OS                : ${PRETTY_NAME:-unknown}"
    else
        echo "OS                : ${OS_NAME}"
    fi
    echo "Kernel            : $(uname -sr)"
    echo "Architecture      : $(uname -m)"
    echo

    echo "--- CPU information ---"
    if [ "$OS_NAME" = "Darwin" ]; then
        echo "Model             : $(sysctl -n machdep.cpu.brand_string 2>/dev/null || echo unknown)"
        echo "Physical cores    : $(sysctl -n hw.physicalcpu 2>/dev/null || echo unknown)"
        echo "Logical cores     : $(sysctl -n hw.logicalcpu 2>/dev/null || echo unknown)"
    elif command -v lscpu >/dev/null 2>&1; then
        lscpu | grep -E 'Model name|^CPU\(s\)|Architecture'
    else
        grep -m1 'model name' /proc/cpuinfo 2>/dev/null || echo "CPU info not available"
        echo "Logical cores     : $(nproc 2>/dev/null || echo unknown)"
    fi
    echo

    echo "--- Memory information ---"
    if [ "$OS_NAME" = "Darwin" ]; then
        MEM_BYTES="$(sysctl -n hw.memsize 2>/dev/null || echo 0)"
        echo "Total memory      : $(( MEM_BYTES / 1024 / 1024 / 1024 )) GB"
        echo "Memory pages (vm_stat):"
        vm_stat | head -n 6
    else
        free -h
    fi
    echo

    echo "--- Disk usage (df -h) ---"
    df -h
    echo

    echo "--- Available disk space on project partition ---"
    df -h "$PROJECT_DIR"
    echo "=============================================="
    echo
}

# tee -a shows output on screen and APPENDS it to the log file (>> behaviour)
report 2>&1 | tee -a "$LOG_FILE"

echo "Report saved to: $LOG_FILE"
