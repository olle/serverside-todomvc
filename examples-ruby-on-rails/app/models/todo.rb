class Todo < ApplicationRecord
  enum :status, { active: 0, completed: 1 }
end
