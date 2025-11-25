# Generar REPORT.pdf y REPORT.html a partir de REPORT.md
# Requisitos:
# - pandoc (https://pandoc.org/)
# - TeX (MiKTeX o TeX Live) si deseas PDF con pdflatex

param(
    [string]$InputFile = "REPORT.md",
    [string]$OutDir = "report",
    [string]$PdfEngine = "pdflatex"
)

# Crear carpeta de salida
if (-not (Test-Path -Path $OutDir)) {
    New-Item -ItemType Directory -Path $OutDir | Out-Null
}

# Generar HTML
Write-Output "Generando HTML..."
pandoc $InputFile -o (Join-Path $OutDir "REPORT.html")

# Generar PDF (si pandoc está instalado y pdflatex disponible)
try {
    Write-Output "Generando PDF..."
    pandoc $InputFile -o (Join-Path $OutDir "REPORT.pdf") --pdf-engine=$PdfEngine --metadata title="Informe - Mueblería"
    Write-Output "PDF generado en $(Join-Path $OutDir 'REPORT.pdf')"
}
catch {
    Write-Output "Error generando PDF: $_"
    Write-Output "Si no tienes LaTeX, genera HTML y conviértelo con otra herramienta o instala MiKTeX/TeX Live."
}
