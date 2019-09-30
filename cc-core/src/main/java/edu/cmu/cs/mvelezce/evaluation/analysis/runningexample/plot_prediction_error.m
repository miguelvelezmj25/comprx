x = [10, 56, 512, 8];
y = [56.91, 6.22, 0.18, 0.07];
n = ["FW", "PW", "SAD", "CC"];

scatter(x,y,1500,'.');
text(x, y, n, 'horizontal','left', 'vertical','bottom')
xlabel('Cost [Configurations]');
ylabel('Prediction Error [MAPE]');
title('Running example');
xlim([-5 600])
ylim([-2 60])
fontset
 
fig = gcf;
fig.PaperPositionMode = 'auto';
fig_pos = fig.PaperPosition;
fig.PaperSize = [fig_pos(3) fig_pos(4)];

mkdir('../../../../../../../../resources/evaluation/programs/java/running-example/plots/');
fileID = '../../../../../../../../resources/evaluation/programs/java/running-example/plots/prediction_error.pdf';
print(fig, fileID,'-dpdf');
fileID = '../../../../../../../../resources/evaluation/programs/java/running-example/plots/prediction_error.png';
print(fig, fileID,'-dpng');