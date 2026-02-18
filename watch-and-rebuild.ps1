# Script de watch qui rebuild automatiquement un service quand ses fichiers changent
# Usage: .\watch-and-rebuild.ps1 <service-name>
# Example: .\watch-and-rebuild.ps1 auth-service

param(
    [Parameter(Mandatory=$true)]
    [ValidateSet('auth-service', 'quiz-service', 'websocket-service', 'api-gateway', 'eureka-server')]
    [string]$ServiceName
)

$servicePath = "backend\$ServiceName\src"
$debounceTime = 2 # secondes avant de rebuild après un changement

Write-Host "👀 Watching $ServiceName for changes..." -ForegroundColor Green
Write-Host "📁 Path: $servicePath" -ForegroundColor Cyan
Write-Host "Press Ctrl+C to stop`n" -ForegroundColor Yellow

# File System Watcher
$watcher = New-Object System.IO.FileSystemWatcher
$watcher.Path = $servicePath
$watcher.IncludeSubdirectories = $true
$watcher.EnableRaisingEvents = $true

# Filtres pour les fichiers Java et resources
$watcher.Filter = "*.*"

# Debounce timer
$timer = $null
$lastChange = Get-Date

# Action à exécuter sur changement
$action = {
    $path = $Event.SourceEventArgs.FullPath
    $changeType = $Event.SourceEventArgs.ChangeType
    
    # Ignorer les fichiers temporaires et .class
    if ($path -match '\.(class|tmp|swp)$') { return }
    
    $now = Get-Date
    $global:lastChange = $now
    
    Write-Host "`n🔔 Change detected: $changeType - $path" -ForegroundColor Yellow
    Write-Host "⏳ Waiting for more changes..." -ForegroundColor Gray
    
    # Attendre le debounce puis rebuild
    Start-Sleep -Seconds $debounceTime
    
    if (((Get-Date) - $global:lastChange).TotalSeconds -ge $debounceTime) {
        Write-Host "`n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Blue
        & ".\rebuild-service.ps1" -ServiceName $using:ServiceName
        Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━`n" -ForegroundColor Blue
        Write-Host "👀 Watching for more changes..." -ForegroundColor Green
    }
}

# Register les events
$handlers = @()
$handlers += Register-ObjectEvent $watcher "Changed" -Action $action
$handlers += Register-ObjectEvent $watcher "Created" -Action $action
$handlers += Register-ObjectEvent $watcher "Deleted" -Action $action
$handlers += Register-ObjectEvent $watcher "Renamed" -Action $action

try {
    while ($true) {
        Start-Sleep -Seconds 1
    }
}
finally {
    # Cleanup
    $handlers | ForEach-Object { Unregister-Event -SourceIdentifier $_.Name }
    $watcher.Dispose()
    Write-Host "`n👋 Stopped watching." -ForegroundColor Yellow
}
