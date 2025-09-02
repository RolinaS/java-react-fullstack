import { useEffect, useMemo, useState } from 'react'
import { api } from './lib/api'

type Todo = {
  id: number
  title: string
  completed: boolean
  createdAt: string
}

export default function App() {
  const [todos, setTodos] = useState<Todo[]>([])
  const [title, setTitle] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const sorted = useMemo(
    () => [...todos].sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()),
    [todos]
  )

  async function load() {
    setLoading(true)
    setError(null)
    try {
      const { data } = await api.get<Todo[]>('/todos')
      setTodos(data)
    } catch (e) {
      setError('Failed to load todos')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    load()
  }, [])

  async function addTodo() {
    if (!title.trim()) return
    const { data } = await api.post<Todo>('/todos', { title })
    setTodos((prev) => [data, ...prev])
    setTitle('')
  }

  async function toggle(todo: Todo) {
    const { data } = await api.put<Todo>(`/todos/${todo.id}`, {
      title: todo.title,
      completed: !todo.completed
    })
    setTodos((prev) => prev.map((t) => (t.id === todo.id ? data : t)))
  }

  async function remove(id: number) {
    await api.delete(`/todos/${id}`)
    setTodos((prev) => prev.filter((t) => t.id !== id))
  }

  return (
    <div style={{ maxWidth: 720, margin: '2rem auto', fontFamily: 'system-ui, sans-serif' }}>
      <h1>Todos</h1>
      <div style={{ display: 'flex', gap: 8 }}>
        <input
          placeholder="New todo title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && addTodo()}
          style={{ flex: 1, padding: 8 }}
        />
        <button onClick={addTodo}>Add</button>
      </div>

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'crimson' }}>{error}</p>}

      <ul style={{ listStyle: 'none', padding: 0, marginTop: 16 }}>
        {sorted.map((t) => (
          <li key={t.id} style={{ display: 'flex', alignItems: 'center', gap: 8, padding: '8px 0' }}>
            <input type="checkbox" checked={t.completed} onChange={() => toggle(t)} />
            <span style={{ textDecoration: t.completed ? 'line-through' : 'none', flex: 1 }}>{t.title}</span>
            <button onClick={() => remove(t.id)} aria-label={`Delete ${t.title}`}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  )
} 