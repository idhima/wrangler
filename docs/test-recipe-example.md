# Sample Test Recipe for `aggregate-stats`

### ✅ Input Rows

| data_transfer_size | response_time |
|--------------------|---------------|
| 10MB               | 1.5s          |
| 5MB                | 2.5s          |

---

### 🧪 Recipe

aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec
---

### ✅ Expected Output

| total_size_mb | total_time_sec |
|---------------|----------------|
| 15.0          | 4.0            |

---

### 💡 Notes
- `10MB + 5MB = 15.0MB`
- `1.5s + 2.5s = 4.0s`
