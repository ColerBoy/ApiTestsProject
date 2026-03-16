# Set UTF-8 encoding for proper character display
chcp 65001

# Define the project root as the current directory (where the script is run)
$projectRoot = Get-Location
$targetFolder = Join-Path -Path $projectRoot -ChildPath "target"
$allureResultsFolder = Join-Path -Path $targetFolder -ChildPath "allure-results"
$allureReportFolder = Join-Path -Path $targetFolder -ChildPath "site\allure-maven-plugin"
$historyFolder = Join-Path -Path $projectRoot -ChildPath "allure-history"

# Step 1: Run tests
Write-Host "Running 'mvn clean verify' to generate test results..."
mvn clean verify

# Step 2: Copy previous history to allure-results before generating the report
if (Test-Path -Path $historyFolder) {
    $historySource = Join-Path -Path $historyFolder -ChildPath "history"
    $historyDestination = Join-Path -Path $allureResultsFolder -ChildPath "history"
    if (Test-Path -Path $historySource) {
        Write-Host "Copying previous history to allure-results for trend continuity..."
        # Ensure the destination directory exists
        if (-not (Test-Path -Path $allureResultsFolder)) {
            New-Item -Path $allureResultsFolder -ItemType Directory -Force
        }
        Copy-Item -Path $historySource -Destination $historyDestination -Recurse -Force
    } else {
        Write-Host "No previous history found. Starting with current run only."
    }
} else {
    Write-Host "No history backup folder exists. Starting fresh."
}

# Step 3: Generate the Allure report with merged history
Write-Host "Generating Allure report with 'mvn allure:report'..."
mvn allure:report

# Step 4: Update the history backup with the new history from the report
$newHistorySource = Join-Path -Path $allureReportFolder -ChildPath "history"
if (Test-Path -Path $newHistorySource) {
    Write-Host "Updating history backup with new history data..."
    # Ensure the backup folder exists
    if (-not (Test-Path -Path $historyFolder)) {
        New-Item -Path $historyFolder -ItemType Directory -Force
    }
    Copy-Item -Path $newHistorySource -Destination $historyFolder -Recurse -Force
} else {
    Write-Host "No new history data generated in report."
}

# Step 5: Serve the report
Write-Host "Serving Allure report with 'mvn allure:serve'... (Press Ctrl+C to stop)"
try {
    mvn allure:serve
} finally {
# Step 6: Clean up lingering Allure server process https://github.com/allure-framework/allure-maven/issues/245
    Write-Host "Cleaning up lingering Allure server process..."
    Get-Process | Where-Object { $_.Path -like "*java*" -and $_.CommandLine -like "*allure*" } | Stop-Process -Force -ErrorAction SilentlyContinue
    Write-Host "Cleanup completed."
}