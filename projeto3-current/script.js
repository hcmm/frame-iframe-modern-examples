function loadContent(url) {
    const htmlContent = document.getElementById('htmlContent');
    const pdfViewer = document.getElementById('pdfViewer');

    htmlContent.innerHTML = 'Carregando...';
    pdfViewer.innerHTML = '';
    
    fetch(url)
        .then(response => response.text())
        .then(data => {
            htmlContent.innerHTML = data;
        })
        .catch(error => {
            htmlContent.innerHTML = 'Erro ao carregar o conteúdo.';
            console.error('Erro:', error);
        });
}

function loadPDF(url) {
    const htmlContent = document.getElementById('htmlContent');
    const pdfViewer = document.getElementById('pdfViewer');

    htmlContent.innerHTML = '';
    pdfViewer.innerHTML = 'Carregando PDF...';

    const loadingTask = pdfjsLib.getDocument(url);
    loadingTask.promise.then(pdf => {
        pdf.getPage(1).then(page => {
            const viewport = page.getViewport({ scale: 1.5 });
            const canvas = document.createElement('canvas');
            const context = canvas.getContext('2d');
            canvas.height = viewport.height;
            canvas.width = viewport.width;
            pdfViewer.appendChild(canvas);

            const renderContext = {
                canvasContext: context,
                viewport: viewport
            };
            page.render(renderContext);
        });
    }).catch(error => {
        pdfViewer.innerHTML = 'Erro ao carregar o PDF.';
        console.error('Erro:', error);
    });
}
