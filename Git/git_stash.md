# git stash

- **git stash命令之后，再执行git status时，提示工作区是一个干净的工作区**

## 使用场景：

- 在pull代码的时候，有时候会提示有文件冲突，需要使用git stash命令会将暂存区和工作区的改动保存起来；
- 正在dev分支开发新功能，做到一半时有人过来反馈一个bug，让马上解决，但是新功能做到了一半你又不想提交，这时就可以使用git stash命令先把当前进度保存起来，然后切换到另一个分支去修改bug，修改完提交后，再切回dev分支，使用git stash pop来恢复之前的进度继续开发新功能;

## 使用

### 命令：git stash

保存当前工作进度，会把暂存区和工作区的改动保存起来。执行完这个命令后，在运行git status命令，就会发现当前是一个干净的工作区，没有任何改动。

使用git stash save 'message...' 可以添加一些注释。

### 命令：git stash list

显示保存进度的列表。也就意味着，git stash命令可以多次执行。

### 命令：git stash pop [–index] [stash_id]

- git stash pop :恢复最新的进度到工作区。git默认会把工作区和暂存区的改动都恢复到工作区。
- git stash pop --index :恢复最新的进度到工作区和暂存区。（尝试将原来暂存区的改动恢复到暂存区）。
- git stash pop stash@{1} :恢复指定的进度到工作区。stash_id是通过git stash list命令得到的。

> git stash pop 命令恢复进度后，会删除当前进度。

### 命令： git stash apply [-index] [stash_id]

除了不删除恢复的进度之外，其余和git stash pop命令一样。

### 命令： git stash drop [stash_id]

删除一个存储的进度。如果不指定stash_id，则默认删除最新的存储进度。

### 命令： git stash clear

删除所有存储的进度。